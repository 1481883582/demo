package com.openfeign_demo.web.controller;

import com.openfeign_demo.http.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "测试接口", tags = "测试接口")
@RestController
@RequestMapping("/openFeign")
public class OpenFeignController {

	@Autowired
	private TestService testService;

	@ApiOperation(value = "测试get", tags = "测试get", httpMethod = "GET")
	@GetMapping("/get")
	public Map<String, Object> get() {
		Map<String, Object> map = testService.get();
		return map;
	}

	@ApiOperation(value = "测试post", tags = "测试post", httpMethod = "POST")
	@PostMapping("/post")
	public String post(@RequestParam("msg") String msg) {
		String post = testService.post(msg);
		return post;
	}

	@ApiOperation(value = "测试put", tags = "测试put", httpMethod = "PUT")
	@PutMapping("/put/{msg}")
	public String put(@PathVariable String msg) {
		return testService.put(msg);
	}

	@ApiOperation(value = "测试delete", tags = "测试delete", httpMethod = "DELETE")
	@DeleteMapping("/delete")
	public String delete(@RequestParam(value = "id", required = false) Long id) {
		return testService.delete(id);
	}

}
