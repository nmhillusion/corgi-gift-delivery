package tech.nmhillusion.corgi_gift_delivery.parser;

import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-08
 */
public interface ExcelSheetParser<E> {
    List<E> parse(SheetData sheetData);
}
