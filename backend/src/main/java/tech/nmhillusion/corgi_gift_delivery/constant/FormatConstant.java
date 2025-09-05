package tech.nmhillusion.corgi_gift_delivery.constant;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-09-05
 */
public enum FormatConstant {
    DATE_FORMAT("yyyy-MM-dd");

    private final String formatValue;

    FormatConstant(String formatValue) {
        this.formatValue = formatValue;
    }

    public String getFormatValue() {
        return formatValue;
    }
}
