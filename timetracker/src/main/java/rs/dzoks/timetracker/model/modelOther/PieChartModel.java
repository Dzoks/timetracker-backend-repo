package rs.dzoks.timetracker.model.modelOther;

import java.math.BigDecimal;

public class PieChartModel {
    private Integer id;
    private String text;
    private BigDecimal value;

    public PieChartModel(){}


    public PieChartModel(Integer id, String text, BigDecimal value) {
        this.id = id;
        this.text = text;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
