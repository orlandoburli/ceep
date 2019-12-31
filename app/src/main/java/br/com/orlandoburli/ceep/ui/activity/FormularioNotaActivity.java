package br.com.orlandoburli.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.orlandoburli.ceep.R;
import br.com.orlandoburli.ceep.dao.NotaDAO;
import br.com.orlandoburli.ceep.model.Nota;

import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

public class FormularioNotaActivity extends AppCompatActivity {


    private final NotaDAO dao = new NotaDAO ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_formulario_nota );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.menu_formulario_nota_salva, menu );
        return super.onCreateOptionsMenu ( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (isSaveMenu ( item )) {
            criarNota ();
        }
        return super.onOptionsItemSelected ( item );
    }

    private void criarNota() {
        EditText titulo    = findViewById ( R.id.formulario_nota_titulo );
        EditText descricao = findViewById ( R.id.formulario_nota_descricao );

        Nota nota = new Nota ( titulo.getText ().toString (), descricao.getText ().toString () );

        dao.insere ( nota );

        retornaNota ( nota );

        finish ();
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent ();
        resultadoInsercao.putExtra ( CHAVE_NOTA, nota );

        setResult ( CODIGO_RESULTADO_NOTA_CRIADA, resultadoInsercao );
    }

    private boolean isSaveMenu(@NonNull MenuItem item) {
        return item.getItemId () == R.id.menu_formulario_nota_ic_salva;
    }
}
