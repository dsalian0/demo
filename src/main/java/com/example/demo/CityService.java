package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class CityService {
	private static final Logger logger = LoggerFactory.getLogger(CityService.class);
	private Map<String, List<String>> cityAdjMatrix;

	public String pathExist(String origin, String destination) {
		final Map<String,Boolean> visited = new HashMap<>();
		final Queue<String> processQueue = new LinkedList<>();
		boolean pathExist = false;
		
		processQueue.add(origin);
		
		while(!processQueue.isEmpty()) {
			String curCity = processQueue.poll();
			if(curCity.equalsIgnoreCase(destination)) {
				pathExist = true;
				break;
			}
			
			visited.put(curCity, true);
			
			cityAdjMatrix.get(curCity).stream()
					.filter(c->!visited.containsKey(c))
					.forEach(c->processQueue.add(c));
			
		}
		
		return pathExist?"Yes":"No";
	}

	@PostConstruct
	public void loadCityData() {
		cityAdjMatrix = new HashMap<>();
		try {
			InputStream stream = new ClassPathResource("data/citydata.txt").getInputStream();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
				String currentLine;
				while ((currentLine = reader.readLine()) != null) {
					String[] pathData = currentLine.split(",");
					String sourceCity = pathData[0].trim();
					String destinationCity = pathData[1].trim();
					if (!cityAdjMatrix.containsKey(sourceCity)) {
						cityAdjMatrix.put(sourceCity, new ArrayList<>());
					}
					if (!cityAdjMatrix.containsKey(destinationCity)) {
						cityAdjMatrix.put(destinationCity, new ArrayList<>());
					}
					cityAdjMatrix.get(sourceCity).add(destinationCity);
					cityAdjMatrix.get(destinationCity).add(sourceCity);
				}
			}
		} catch (IOException e) {
			logger.error("Unable to load city data");
		}
	}
}