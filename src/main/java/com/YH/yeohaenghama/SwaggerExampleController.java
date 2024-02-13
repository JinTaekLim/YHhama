package com.YH.yeohaenghama;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Tag(name = "스웨거 예시 컨트롤")
public class SwaggerExampleController {

    @Operation(description = "데이터 조회")
    @GetMapping
    public String test() {
        return "스웨거 적용 테스트";
    }

}
