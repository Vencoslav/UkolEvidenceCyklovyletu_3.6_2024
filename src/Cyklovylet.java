import java.time.LocalDate;

public class Cyklovylet {
    private String cil;
    private Integer delka;
    private LocalDate date;
    public Cyklovylet(String cil, Integer delka, LocalDate date) {
        this.cil = cil;
        this.delka = delka;
        this.date = date;
    }

    public String getCil() {
        return cil;
    }

    public Integer getDelka() {
        return delka;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setCil(String cil) {
        this.cil = cil;
    }

    public void setDelka(Integer delka) {
        this.delka = delka;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
