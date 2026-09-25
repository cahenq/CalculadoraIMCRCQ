package br.unir.nutrilife;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.app.Activity;

public class MainActivity extends Activity {
    private EditText nome, peso, altura, cintura, quadril;
    private RadioGroup sexo;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b); setContentView(R.layout.activity_main);
        nome=findViewById(R.id.edtNome); peso=findViewById(R.id.edtPeso); altura=findViewById(R.id.edtAltura);
        cintura=findViewById(R.id.edtCintura); quadril=findViewById(R.id.edtQuadril); sexo=findViewById(R.id.rgSexo);
        findViewById(R.id.btnCalcular).setOnClickListener(v -> validar());
    }

    private double numero(EditText e) { return Double.parseDouble(e.getText().toString().trim().replace(',', '.')); }

    private void validar() {
        String n=nome.getText().toString().trim();
        if(n.isEmpty() || peso.getText().toString().trim().isEmpty() || altura.getText().toString().trim().isEmpty() ||
           cintura.getText().toString().trim().isEmpty() || quadril.getText().toString().trim().isEmpty() || sexo.getCheckedRadioButtonId()==-1) {
            erro(); return;
        }
        try {
            double p=numero(peso), a=numero(altura), c=numero(cintura), q=numero(quadril);
            if(p<=0 || a<=0 || c<=0 || q<=0 || a>3) { erro(); return; }
            new AlertDialog.Builder(this).setTitle("Confirmar cálculo")
                .setMessage("Os dados informados estão corretos?")
                .setNegativeButton("NÃO", null).setPositiveButton("SIM", (d,w) -> calcular(n,p,a,c,q)).show();
        } catch(Exception ex) { erro(); }
    }

    private void erro() { new AlertDialog.Builder(this).setTitle("Dados incompletos").setMessage("Preencha todos os campos corretamente e selecione o sexo.").setPositiveButton("OK", null).show(); }

    private void calcular(String n,double p,double a,double c,double q) {
        double imc=p/(a*a), rcq=c/q;
        String s = sexo.getCheckedRadioButtonId()==R.id.rbMasculino ? "Masculino" : "Feminino";
        Intent i=new Intent(this, ResultadoActivity.class);
        i.putExtra("nome",n); i.putExtra("imc",imc); i.putExtra("rcq",rcq); i.putExtra("sexo",s);
        startActivity(i);
    }
}
