package br.com.bgrbarbosa.ms_scheduling.exams.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyConverter extends JsonSerializer {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            // Cria um formatador de moeda para o padrão brasileiro
            NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            // Formata o valor double para uma String
            String valorFormatado = formatoMoeda.format(value);

            // Escreve a String formatada no JSON
            gen.writeString(valorFormatado);
        }
    }
}
