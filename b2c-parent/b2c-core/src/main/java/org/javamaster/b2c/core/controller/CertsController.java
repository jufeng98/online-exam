package org.javamaster.b2c.core.controller;

import com.github.pagehelper.PageInfo;
import org.javamaster.b2c.core.entity.Certs;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.model.vo.CreateCertsReqVo;
import org.javamaster.b2c.core.model.vo.CreateCertsResVo;
import org.javamaster.b2c.core.model.vo.DelCertsReqVo;
import org.javamaster.b2c.core.model.vo.EditCertsReqVo;
import org.javamaster.b2c.core.model.vo.FindCertsListReqVo;
import org.javamaster.b2c.core.service.CertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 证书管理
 *
 * @author yudong
 * @date 2019/08/11
 */
@RestController
@RequestMapping("/core/certs")
public class CertsController {

    @Autowired
    private CertsService certsService;

    @GetMapping("/findCertsExamplePdf")
    public ResponseEntity<byte[]> findCertsExamplePdf(Integer id) {
        byte[] bytes = certsService.findCertsExamplePdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @PostMapping("/findCertsList")
    public Result<List<Certs>> findCertsList(@Validated @RequestBody FindCertsListReqVo reqVo) {
        PageInfo<Certs> resVo = certsService.findCertsList(reqVo);
        return new Result<>(resVo.getList(), resVo.getTotal());
    }

    @PostMapping("/createCerts")
    public Result<CreateCertsResVo> createCerts(@Validated @RequestBody CreateCertsReqVo reqVo,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        CreateCertsResVo resVo = certsService.createCerts(reqVo, userDetails);
        return new Result<>(resVo);
    }

    @PostMapping("/editCerts")
    public Result<Integer> editCerts(@Validated @RequestBody EditCertsReqVo reqVo) {
        Integer resVo = certsService.editCerts(reqVo);
        return new Result<>(resVo);
    }

    @PostMapping("/delCerts")
    public Result<Integer> delCerts(@Validated @RequestBody DelCertsReqVo reqVo) {
        Integer resVo = certsService.delCerts(reqVo);
        return new Result<>(resVo);
    }

}