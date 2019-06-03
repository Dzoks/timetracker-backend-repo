package rs.dzoks.timetracker.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Setting {
    private Integer id;
    private String key;
    private String value;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "key", nullable = false, length = 45)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "value", nullable = false, length = 45)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting setting = (Setting) o;
        return Objects.equals(id, setting.id) &&
                Objects.equals(key, setting.key) &&
                Objects.equals(value, setting.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, value);
    }
}
