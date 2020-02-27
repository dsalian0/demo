package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = {"Demo"})
public class CityController {
	
	@Autowired
	private CityService cityService;

	@GetMapping(path="/connected" )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Failure")})
    public String getCityInfo(@RequestParam(value="origin", required = true) String origin,
    		@RequestParam(value="destination", required = true) String destination) 
    {
        return cityService.pathExist(origin, destination);
    }
}
