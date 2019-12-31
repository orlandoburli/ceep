package br.com.orlandoburli.ceep.ui.activity;

import android.app.Activity;
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
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";
    private final NotaDAO dao = new NotaDAO ();
    private Nota nota;
    private int posicao;
    private EditText titulo;
    private EditText descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_formulario_nota );

        Intent intent = getIntent ();

        inicializaCampos ();

        setTitle ( TITULO_APPBAR_INSERE );

        if (intent.hasExtra ( CHAVE_NOTA ) && intent.hasExtra ( CHAVE_POSICAO )) {
            nota = (Nota) intent.getSerializableExtra ( CHAVE_NOTA );
            posicao = intent.getExtras ().getInt ( CHAVE_POSICAO, POSICAO_INVALIDA );

            preencheCampos ();

            setTitle ( TITULO_APPBAR_ALTERA );
        }
    }

    private void inicializaCampos() {
        titulo = findViewById ( R.id.formulario_nota_titulo );
        descricao = findViewById ( R.id.formulario_nota_descricao );
    }

    private void preencheCampos() {
        titulo.setText ( nota.getTitulo () );
        descricao.setText ( nota.getDescricao () );
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
        Nota nota = new Nota ( titulo.getText ().toString (), descricao.getText ().toString () );
        dao.insere ( nota );
        retornaNota ( nota );
        finish ();
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent ();
        resultadoInsercao.putExtra ( CHAVE_NOTA, nota );
        resultadoInsercao.putExtra ( CHAVE_POSICAO, posicao );

        setResult ( Activity.RESULT_OK, resultadoInsercao );
    }

    private boolean isSaveMenu(@NonNull MenuItem item) {
        return item.getItemId () == R.id.menu_formulario_nota_ic_salva;
    }
}
