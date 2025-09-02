package tech.nmhillusion.corgi_gift_delivery.domains.base;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.BaseBusinessEntity;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseDeliveryService;
import tech.nmhillusion.n2mix.constant.ContentType;

import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */

public abstract class BaseDeliveryController<E extends BaseBusinessEntity<Long>, DTO> {
    protected final BaseDeliveryService<E, DTO> service;

    public BaseDeliveryController(BaseDeliveryService<E, DTO> service) {
        this.service = service;
    }


    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<E> search(@RequestBody DTO dto,
                          @RequestParam(defaultValue = "0") int pageIndex,
                          @RequestParam(defaultValue = "10") int pageSize) {
        return service.search(
                dto
                , pageIndex
                , pageSize
        );
    }

    @PostMapping(value = "/insert/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<E> insertBatchByExcelFile(@RequestBody MultipartFile excelFile) {
        return service.insertBatchByExcelFile(excelFile);
    }

    @PostMapping(value = "/update/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<E> updateBatchByExcelFile(@RequestBody MultipartFile excelFile) {
        return service.updateBatchByExcelFile(excelFile);
    }

    @PostMapping(value = "/export", produces = ContentType.MS_EXCEL_XLSX)
    public Resource export(@RequestBody DTO dto) {
        return service.export(dto);
    }
}
