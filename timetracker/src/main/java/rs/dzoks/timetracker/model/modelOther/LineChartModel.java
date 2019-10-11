package rs.dzoks.timetracker.model.modelOther;

import java.math.BigDecimal;
import java.util.Date;

public class LineChartModel {

    private Date date;
    private BigDecimal value;

    public LineChartModel(Date date, BigDecimal value) {
        this.date = date;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
