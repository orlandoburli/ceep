package br.com.orlandoburli.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import br.com.orlandoburli.ceep.R;
import br.com.orlandoburli.ceep.dao.NotaDAO;
import br.com.orlandoburli.ceep.model.Nota;
import br.com.orlandoburli.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String APP_BAR_TITLE = "Lista de Notas";
    private final NotaDAO dao = new NotaDAO ();
    private ListaNotasAdapter listaNotasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_lista_notas );

        setTitle ( APP_BAR_TITLE );

        configRecyclerView ();

        configureInsereNotaListener ();
    }

    private void configureInsereNotaListener() {
        TextView insereNota = findViewById ( R.id.lista_notas_insere_nota );

        insereNota.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                vaiParaNotaActivity ();
            }
        } );
    }

    private void vaiParaNotaActivity() {
        Intent intent = new Intent ( ListaNotasActivity.this, FormularioNotaActivity.class );
        startActivityForResult ( intent, CODIGO_REQUISICAO_INSERE_NOTA );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ehResultadoComNota ( requestCode, resultCode, data )) {
            Nota nota = (Nota) data.getSerializableExtra ( CHAVE_NOTA );
            adicionarNota ( nota );
        }

        super.onActivityResult ( requestCode, resultCode, data );
    }

    private void adicionarNota(Nota nota) {
        dao.insere ( nota );
        this.listaNotasAdapter.adicionar ( nota );
    }

    private boolean ehResultadoComNota(int requestCode, int resultCode, @Nullable Intent data) {
        return ehCodigoRequisicaoInsereNota ( requestCode ) && ehCodigoResultadoNotaCriada ( resultCode ) && temNota ( data );
    }

    private boolean temNota(@Nullable Intent data) {
        return data.hasExtra ( CHAVE_NOTA );
    }

    private boolean ehCodigoResultadoNotaCriada(int resultCode) {
        return resultCode == CODIGO_RESULTADO_NOTA_CRIADA;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configRecyclerView() {
        final RecyclerView listaNotas = findViewById ( R.id.lista_notas_recyclerview );

        configAdapter (listaNotas);
    }

    private RecyclerView configAdapter(RecyclerView listaNotas) {
        listaNotasAdapter = new ListaNotasAdapter ( this, dao.todos () );

        listaNotas.setAdapter ( listaNotasAdapter );

        return listaNotas;
    }
}