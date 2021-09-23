package com.openfeign_demo.http;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用于测试和示例  openfeign
 */
@Configuration
@FeignClient(url = "http://127.0.0.1:9000", name = "swaggerDome")
@RequestMapping("/test")
public interface TestService {

	@ApiOperation(value = "测试get", tags = "测试get")
	@GetMapping("/get")
	public Map<String, Object> get();

	@ApiOperation(value = "测试post", tags = "测试post")
	@PostMapping("/post")
	public String post(@RequestParam("msg") String msg);

	@ApiOperation(value = "测试put", tags = "测试put")
	@PutMapping("/put/{msg}")
	public String put(@PathVariable String msg);

	@ApiOperation(value = "测试delete", tags = "测试delete")
	@DeleteMapping("/delete")
	public String delete(@RequestParam(value = "id", required = false) Long id);
}