package org.javamaster.b2c.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.javamaster.b2c.core.entity.Certs;
import org.javamaster.b2c.core.entity.CertsExample;
import org.javamaster.b2c.core.mapper.CertsMapper;
import org.javamaster.b2c.core.model.vo.CreateCertsReqVo;
import org.javamaster.b2c.core.model.vo.CreateCertsResVo;
import org.javamaster.b2c.core.model.vo.DelCertsReqVo;
import org.javamaster.b2c.core.model.vo.EditCertsReqVo;
import org.javamaster.b2c.core.model.vo.FindCertsListReqVo;
import org.javamaster.b2c.core.service.CertsService;
import org.javamaster.b2c.core.service.FilesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * @author yudong
 * @date 2019/08/11
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class CertsServiceImpl implements CertsService {

    @Autowired
    private CertsMapper certsMapper;
    @Autowired
    private FilesService filesService;

    @Override
    @SneakyThrows
    public byte[] findCertsExamplePdf(Integer id) {
        Certs certs = certsMapper.selectByPrimaryKey(id);
        String picUrl = certs.getPicUrl();
        Image image;
        if (StringUtils.isNotBlank(picUrl)) {
            URI uri = URI.create(picUrl);
            String query =uri.getQuery().split("=")[1];
            byte[] bytes = filesService.downloadFile(query);
            image = Image.getInstance(bytes);
        } else {
            File file = ResourceUtils.getFile("classpath:images/certificate.jpg");
            image = Image.getInstance(file.getAbsolutePath());
        }
        Document document = new Document();
        @Cleanup ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.setPageSize(new Rectangle(image.getWidth(), image.getHeight()));
        document.setMargins(0, 0, 0, 0);
        document.open();
        document.add(image);
        String acquireMan;
        String topics;
        String acquireTime;
        acquireMan = "jufeng98";
        topics = certs.getCertsName();
        acquireTime = "获得时间: " + DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        appendTextToDoc(writer, acquireMan, 250, 720);
        appendTextToDoc(writer, topics, 300, 600);
        appendTextToDoc(writer, acquireTime, 1000, 1030);
        document.close();
        return outputStream.toByteArray();
    }

    @SneakyThrows
    private void appendTextToDoc(PdfWriter writer, String txt, int x, int y) {
        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        cb.beginText();
        cb.setFontAndSize(bf, 38);
        cb.setCharacterSpacing(5);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, txt, x, y, 0);
        cb.endText();
    }

    @Override
    public PageInfo<Certs> findCertsList(FindCertsListReqVo reqVo) {
        CertsExample certsExample = new CertsExample();
        CertsExample.Criteria criteria = certsExample.createCriteria();
        if (StringUtils.isNotBlank(reqVo.getCertsForm().getCertsName())) {
            criteria.andCertsNameLike(reqVo.getCertsForm().getCertsName() + "%");
        }
        PageHelper.startPage(reqVo.getPage().getPageNum(), reqVo.getPage().getPageSize(), "create_time desc");
        List<Certs> certs = certsMapper.selectByExample(certsExample);
        return new PageInfo<>(certs);
    }

    @Override
    public CreateCertsResVo createCerts(CreateCertsReqVo reqVo, UserDetails userDetails) {
        Certs certs = new Certs();
        BeanUtils.copyProperties(reqVo.getCertsForm(), certs);
        certs.setCreateUsername(userDetails.getUsername());
        certsMapper.insertSelective(certs);
        CreateCertsResVo createCertsResVo = new CreateCertsResVo();
        createCertsResVo.setCerts(certs);
        return createCertsResVo;
    }

    @Override
    public Integer editCerts(EditCertsReqVo reqVo) {
        Certs certs = new Certs();
        BeanUtils.copyProperties(reqVo.getCertsForm(), certs);
        return certsMapper.updateByPrimaryKeySelective(certs);
    }

    @Override
    public Integer delCerts(DelCertsReqVo reqVo) {
        return certsMapper.deleteByPrimaryKey(reqVo.getId());
    }

}