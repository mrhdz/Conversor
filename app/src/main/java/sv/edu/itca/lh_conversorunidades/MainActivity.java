package sv.edu.itca.lh_conversorunidades;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Spinner spnUnidadesPeso;
    private String strValor;
    private TextView txtGramos;
    private TextView txtLibras;
    private TextView txtKilos;
    private TextView txtToneladas;

    private Spinner spnUnidadesLongitud;
    private TextView txtMM;
    private TextView txtCM;
    private TextView txtMT;
    private TextView txtKM;

    private Spinner spnUnidadesTemperatura;
    private TextView txtCelsius;
    private TextView txtFahrenheit;
    private TextView txtKelvin;

    // CONSTANTES PARA CONVERTIR PESO
    private final int GRAMO = 0, LIBRA = 1, KILO = 2, TONELADA = 3;
    private final int QUINIENTOS = 500, MIL = 1000, DOSMIL = 2000, MILLON = 1000000;

    // CONSTANTES PARA CONVERTIR LONGITUD
    private final int MM = 0, CM = 1, MT = 2, KM = 3;
    private final int DIEZ = 10, CIEN = 100, CIENMIL = 100000;

    // CONSTANTES PARA CONVERTIR TEMPERATURA
    private final int CELSIUS = 0, FAHRENHEIT = 1, KELVIN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.miTabHost), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        iniciarVariables();
        configurarBtnConvertir();
        configurarBtnConvertirLongitud();
        configurarBtnConvertirTemperatura();

        Resources res = getResources();

        TabHost tabControl = findViewById(R.id.miTabHost);
        tabControl.setup();

        TabHost.TabSpec spec;

        spec = tabControl.newTabSpec("PESO");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.peso));
        tabControl.addTab(spec);

        spec = tabControl.newTabSpec("LONGITUD");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.longitud));
        tabControl.addTab(spec);

        spec = tabControl.newTabSpec("TEMPERATURA");
        spec.setContent(R.id.tab3);
        spec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.temperatura));
        tabControl.addTab(spec);
    }

    private void iniciarVariables() {
        txtGramos = findViewById(R.id.txtGramos);
        txtLibras = findViewById(R.id.txtlibras);
        txtKilos = findViewById(R.id.txtKilos);
        txtToneladas = findViewById(R.id.txtToneladas);

        txtMM = findViewById(R.id.txtMilimetros);
        txtCM = findViewById(R.id.txtCentimetros);
        txtMT = findViewById(R.id.txtMetros);
        txtKM = findViewById(R.id.txtKilometros);

        txtCelsius = findViewById(R.id.txtCelsius);
        txtFahrenheit = findViewById(R.id.txtFahrenheit);
        txtKelvin = findViewById(R.id.txtKelvin);
    }

    private void configurarBtnConvertir() {
        Button btnConvertirPeso = findViewById(R.id.btnConvertir);
        btnConvertirPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText valorPesoTxt = findViewById(R.id.txtValorPeso);
                strValor = valorPesoTxt.getText().toString().trim();

                // Validar que el campo no esté vacío
                if (strValor.isEmpty()) {
                    valorPesoTxt.setError(getString(R.string.error1));
                    return;
                }

                try {
                    Float valorConvertir = Float.parseFloat(strValor);
                    spnUnidadesPeso = findViewById(R.id.spinnerUnidades);
                    int itemSeleccionado = spnUnidadesPeso.getSelectedItemPosition();

                    // Formato para mostrar solo dos decimales
                    DecimalFormat df = new DecimalFormat(getString(R.string.result));
                    df.setRoundingMode(RoundingMode.HALF_UP);

                    switch (itemSeleccionado) {
                        case GRAMO:
                            txtGramos.setText(df.format(valorConvertir));
                            txtLibras.setText(df.format(gramoLibras(valorConvertir)));
                            txtKilos.setText(df.format(gramoKilos(valorConvertir)));
                            txtToneladas.setText(df.format(gramoToneladas(valorConvertir)));
                            break;
                        case LIBRA:
                            txtGramos.setText(df.format(libraGramos(valorConvertir)));
                            txtLibras.setText(df.format(valorConvertir));
                            txtKilos.setText(df.format(libraKilos(valorConvertir)));
                            txtToneladas.setText(df.format(libraToneladas(valorConvertir)));
                            break;
                        case KILO:
                            txtGramos.setText(df.format(kiloGramos(valorConvertir)));
                            txtLibras.setText(df.format(kiloLibras(valorConvertir)));
                            txtKilos.setText(df.format(valorConvertir));
                            txtToneladas.setText(df.format(kiloToneladas(valorConvertir)));
                            break;
                        case TONELADA:
                            txtGramos.setText(df.format(toneladaGramos(valorConvertir)));
                            txtLibras.setText(df.format(toneladaLibras(valorConvertir)));
                            txtKilos.setText(df.format(toneladaKilos(valorConvertir)));
                            txtToneladas.setText(df.format(valorConvertir));
                            break;
                    }
                } catch (NumberFormatException e) {
                    valorPesoTxt.setError(getString(R.string.error2));
                }
            }
        });
    }

    private void configurarBtnConvertirLongitud() {
        Button btnConvertirLongitud = findViewById(R.id.btnConvertirLongitud);
        btnConvertirLongitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText valorLongitudTxt = findViewById(R.id.txtValorLongitud);
                strValor = valorLongitudTxt.getText().toString().trim();

                // Validar que el campo no esté vacío
                if (TextUtils.isEmpty(strValor)) {
                    valorLongitudTxt.setError(getString(R.string.error1));
                    return;
                }

                try {
                    Float valorConvertir = Float.parseFloat(strValor);

                    // Validar que el valor sea positivo
                    if (valorConvertir < 0) {
                        valorLongitudTxt.setError(getString(R.string.error3));
                        return;
                    }

                    spnUnidadesLongitud = findViewById(R.id.spinnerUnidadesLongitud);
                    int itemSeleccionado = spnUnidadesLongitud.getSelectedItemPosition();

                    // Formato para mostrar solo dos decimales
                    DecimalFormat df = new DecimalFormat(getString(R.string.result));
                    df.setRoundingMode(RoundingMode.HALF_UP);

                    switch (itemSeleccionado) {
                        case MM:
                            txtMM.setText(df.format(valorConvertir));
                            txtCM.setText(df.format(mmACm(valorConvertir)));
                            txtMT.setText(df.format(mmAMt(valorConvertir)));
                            txtKM.setText(df.format(mmAKm(valorConvertir)));
                            break;
                        case CM:
                            txtMM.setText(df.format(cmAMm(valorConvertir)));
                            txtCM.setText(df.format(valorConvertir));
                            txtMT.setText(df.format(cmAMt(valorConvertir)));
                            txtKM.setText(df.format(cmAKm(valorConvertir)));
                            break;
                        case MT:
                            txtMM.setText(df.format(mtAMm(valorConvertir)));
                            txtCM.setText(df.format(mtACm(valorConvertir)));
                            txtMT.setText(df.format(valorConvertir));
                            txtKM.setText(df.format(mtAKm(valorConvertir)));
                            break;
                        case KM:
                            txtMM.setText(df.format(kmAMm(valorConvertir)));
                            txtCM.setText(df.format(kmACm(valorConvertir)));
                            txtMT.setText(df.format(kmAMt(valorConvertir)));
                            txtKM.setText(df.format(valorConvertir));
                            break;
                    }
                } catch (NumberFormatException e) {
                    valorLongitudTxt.setError(getString(R.string.error2));
                }
            }
        });
    }

    private void configurarBtnConvertirTemperatura() {
        Button btnConvertirTemperatura = findViewById(R.id.btnConvertirTemperatura);
        btnConvertirTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText valorTemperaturaTxt = findViewById(R.id.txtValorTemperatura);
                strValor = valorTemperaturaTxt.getText().toString().trim();

                // Validar que el campo no esté vacío
                if (TextUtils.isEmpty(strValor)) {
                    valorTemperaturaTxt.setError(getString(R.string.error1));
                    return;
                }

                try {
                    Float valorConvertir = Float.parseFloat(strValor);

                    spnUnidadesTemperatura = findViewById(R.id.spinnerUnidadesTemperatura);
                    int itemSeleccionado = spnUnidadesTemperatura.getSelectedItemPosition();

                    // Formato para mostrar solo dos decimales
                    DecimalFormat df = new DecimalFormat(getString(R.string.result));
                    df.setRoundingMode(RoundingMode.HALF_UP);

                    // Validar rangos de temperatura
                    if (itemSeleccionado == CELSIUS && valorConvertir < -273.15) {
                        valorTemperaturaTxt.setError(getString(R.string.errortemp1));
                        return;
                    } else if (itemSeleccionado == FAHRENHEIT && valorConvertir < -459.67) {
                        valorTemperaturaTxt.setError(getString(R.string.errortemp2));
                        return;
                    } else if (itemSeleccionado == KELVIN && valorConvertir < 0) {
                        valorTemperaturaTxt.setError(getString(R.string.errortemp3));
                        return;
                    }

                    switch (itemSeleccionado) {
                        case CELSIUS:
                            txtCelsius.setText(df.format(valorConvertir));
                            txtFahrenheit.setText(df.format(celsiusAFahrenheit(valorConvertir)));
                            txtKelvin.setText(df.format(celsiusAKelvin(valorConvertir)));
                            break;
                        case FAHRENHEIT:
                            txtCelsius.setText(df.format(fahrenheitACelsius(valorConvertir)));
                            txtFahrenheit.setText(df.format(valorConvertir));
                            txtKelvin.setText(df.format(fahrenheitAKelvin(valorConvertir)));
                            break;
                        case KELVIN:
                            txtCelsius.setText(df.format(kelvinACelsius(valorConvertir)));
                            txtFahrenheit.setText(df.format(kelvinAFahrenheit(valorConvertir)));
                            txtKelvin.setText(df.format(valorConvertir));
                            break;
                    }
                } catch (NumberFormatException e) {
                    valorTemperaturaTxt.setError(getString(R.string.error2));
                }
            }
        });
    }

    // DE GRAMO A OTRAS UNIDADES
    private Float gramoLibras(Float valorConvertir) { return valorConvertir / QUINIENTOS; }

    private Float gramoKilos(Float valorConvertir) { return valorConvertir / MIL; }

    private Float gramoToneladas(Float valorConvertir) { return valorConvertir / MILLON; }

    // DE LIBRAS A OTRAS UNIDADES
    private Float libraGramos(Float valorConvertir) { return valorConvertir * QUINIENTOS; }

    private Float libraKilos(Float valorConvertir) { return valorConvertir / 2.20462f; } // pra mayor precision

    private Float libraToneladas(Float valorConvertir) { return valorConvertir / DOSMIL; }

    // DE KILOS A OTRAS UNIDADES
    private Float kiloGramos(Float valorConvertir) { return valorConvertir * MIL; }

    private Float kiloLibras(Float valorConvertir) { return valorConvertir * 2.20462f; }

    private Float kiloToneladas(Float valorConvertir) { return valorConvertir / MIL; }

    // DE TONELADAS A OTRAS UNIDADES
    private Float toneladaGramos(Float valorConvertir) { return valorConvertir * MILLON; }

    private Float toneladaLibras(Float valorConvertir) { return valorConvertir * DOSMIL; }

    private Float toneladaKilos(Float valorConvertir) { return valorConvertir * MIL; }

    // Métodos de conversión de longitud
    private Float mmACm(Float valor) { return valor / DIEZ; }

    private Float mmAMt(Float valor) { return valor / MIL; }

    private Float mmAKm(Float valor) { return valor / MILLON; }

    // de cm a otras unidades
    private Float cmAMm(Float valor) { return valor * DIEZ; }

    private Float cmAMt(Float valor) { return valor / CIEN; }

    private Float cmAKm(Float valor) { return valor / CIENMIL; }

    // de mt a otras unidades
    private Float mtAMm(Float valor) { return valor * MIL; }

    private Float mtACm(Float valor) { return valor * CIEN; }

    private Float mtAKm(Float valor) { return valor / MIL; }

    // de km a otras unidades
    private Float kmAMm(Float valor) { return valor * MILLON; }

    private Float kmACm(Float valor) { return valor * CIENMIL; }

    private Float kmAMt(Float valor) { return valor * MIL; }

    // Métodos de conversión de temperatura
    private Float celsiusAFahrenheit(Float valor) { return (valor * 9 / 5) + 32; }

    private Float celsiusAKelvin(Float valor) { return valor + 273.15f; }

    // de fahrenheit a otras unidades
    private Float fahrenheitACelsius(Float valor) { return (valor - 32) * 5 / 9; }

    private Float fahrenheitAKelvin(Float valor) { return (valor + 459.67f) * 5 / 9; }

    // de kelvin a otras unidades
    private Float kelvinACelsius(Float valor) { return valor - 273.15f; }

    private Float kelvinAFahrenheit(Float valor) { return (valor * 9 / 5) - 459.67f; }
}