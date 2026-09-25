package br.unir.nutrilife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Locale;

public class ResultadoActivity extends Activity {
    private String nome, classe, riscoImc, riscoRcq; private double imc, rcq;
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b); setContentView(R.layout.activity_resultado);
        Intent dados=getIntent(); nome=dados.getStringExtra("nome"); imc=dados.getDoubleExtra("imc",0); rcq=dados.getDoubleExtra("rcq",0);
        String sexo=dados.getStringExtra("sexo"); classificarImc(); riscoRcq=("Masculino".equals(sexo) ? rcq<0.90 : rcq<0.85) ? "Normal" : "Risco aumentado";
        ((TextView)findViewById(R.id.txtSaudacao)).setText("Olá, "+nome+"!");
        ((TextView)findViewById(R.id.txtImc)).setText(String.format(Locale.getDefault(),"%.2f kg/m²",imc));
        ((TextView)findViewById(R.id.txtClassificacao)).setText("Classificação: "+classe);
        ((TextView)findViewById(R.id.txtRiscoImc)).setText("O que pode acontecer: "+riscoImc);
        ((TextView)findViewById(R.id.txtRcq)).setText(String.format(Locale.getDefault(),"%.2f",rcq));
        ((TextView)findViewById(R.id.txtRiscoRcq)).setText("Risco relacionado à RCQ: "+riscoRcq);
        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());
        findViewById(R.id.btnCompartilhar).setOnClickListener(v -> compartilhar());
    }
    private void classificarImc(){
        if(imc<17){classe="Muito abaixo do peso"; riscoImc="Maior risco de problemas de saúde, deficiências nutricionais, redução do desempenho físico e fraqueza/letargia";}
        else if(imc<18.5){classe="Abaixo do peso"; riscoImc="Maior risco de problemas relacionados ao baixo peso e deficiências nutricionais";}
        else if(imc<25){classe="Peso normal"; riscoImc="Faixa de peso considerada adequada para a maioria dos adultos";}
        else if(imc<30){classe="Sobrepeso"; riscoImc="Maior risco de alterações metabólicas, diabetes tipo 2 e doenças cardiovasculares";}
        else if(imc<35){classe="Obesidade Grau I"; riscoImc="Risco elevado de diabetes tipo 2, hipertensão e doenças cardiovasculares";}
        else if(imc<40){classe="Obesidade Grau II"; riscoImc="Risco muito elevado de complicações metabólicas, cardiovasculares e respiratórias";}
        else {classe="Obesidade Grau III"; riscoImc="Risco muitíssimo elevado de comorbidades e comprometimento da saúde e qualidade de vida";}
    }
    private void compartilhar(){
        String msg=String.format(Locale.getDefault(),"NutriLife 2.0 — Resultado de %s\nIMC: %.2f kg/m² — %s\nPossíveis riscos/condições: %s\nRCQ: %.2f — %s",nome,imc,classe,riscoImc,rcq,riscoRcq);
        Intent s=new Intent(Intent.ACTION_SEND); s.setType("text/plain"); s.putExtra(Intent.EXTRA_TEXT,msg);
        startActivity(Intent.createChooser(s,"Compartilhar resultado"));
    }
}
