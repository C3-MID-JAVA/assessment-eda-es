package utils;

import java.util.Random;
import java.time.Year;

public class UsesCasesUtil {

    public static String generateAccountNumber(String documentId) {
        // Hash basado en la cédula para mantener unicidad parcial
        int hash = Math.abs(documentId.hashCode() % 100000); // Limitar a 5 dígitos

        // Generar los 3 dígitos restantes aleatoriamente
        Random random = new Random();
        int randomDigits = random.nextInt(900) + 100; // Rango de 100 a 999

        // Obtener el año actual
        int currentYear = Year.now().getValue();

        // Combinar los valores para obtener el número de cuenta final
        return String.format("00%03d%03d%d", hash, randomDigits, currentYear);

    }

}
