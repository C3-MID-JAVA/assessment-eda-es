package ec.com.sofka.entities.accountbank.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.math.BigDecimal;

public class GeneralBalance implements IValueObject<BigDecimal> {
    private BigDecimal value;

    private GeneralBalance(BigDecimal value) {
        this.value = value;
    }

    public static GeneralBalance of(BigDecimal value) {
        return new GeneralBalance(value);
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    private String validate(){
        if (value == null) {
            return "El balance general no puede ser nulo.";
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            return "El balance general no puede ser negativo.";
        }
        return null;
    }

}
