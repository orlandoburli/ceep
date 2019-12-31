package br.com.orlandoburli.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.orlandoburli.ceep.R;
import br.com.orlandoburli.ceep.dao.NotaDAO;
import br.com.orlandoburli.ceep.model.Nota;
import br.com.orlandoburli.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.orlandoburli.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;
import br.com.orlandoburli.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;
import static br.com.orlandoburli.ceep.ui.activity.NotaActivityConstantes.TITULO_APPBAR;

public class ListaNotasActivity extends AppCompatActivity {

    private final NotaDAO dao = new NotaDAO ();
    private ListaNotasAdapter listaNotasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        for (int i = 0; i < 4; i++) {
            dao.insere ( new Nota ( "Título " + (i + 1), "Descrição " + (i + 1) ) );
        }

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_lista_notas );

        setTitle ( TITULO_APPBAR );

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

        if (ehResultadoInsereNota ( requestCode, data )) {
            if (ehResultadoOk ( resultCode )) {
                Nota nota = (Nota) data.getSerializableExtra ( CHAVE_NOTA );
                adicionarNota ( nota );
            } else if (ehResultadoCancelado ( resultCode )) {
                Toast.makeText ( this, "Você cancelou a inclusão da nota", Toast.LENGTH_SHORT ).show ();
            }
        } else if (ehResultadoAlteraNota ( requestCode, data )) {
            if (ehResultadoOk ( resultCode )) {
                Nota nota = (Nota) data.getSerializableExtra ( CHAVE_NOTA );

                int posicao = data.getIntExtra ( CHAVE_POSICAO, POSICAO_INVALIDA );

                if (ehPosicaoValida ( posicao )) {
                    alterarNota ( nota, posicao );
                } else {
                    Toast.makeText ( this, "Ocorreu um problema na alteração da nota", Toast.LENGTH_SHORT ).show ();
                }
            } else if (ehResultadoCancelado ( resultCode )) {
                Toast.makeText ( this, "Você cancelou as alterações na nota", Toast.LENGTH_SHORT ).show ();
            }
        }

        super.onActivityResult ( requestCode, resultCode, data );
    }

    private boolean ehResultadoCancelado(int resultCode) {
        return resultCode == Activity.RESULT_CANCELED;
    }

    private void alterarNota(Nota nota, int posicao) {
        dao.altera ( posicao, nota );
        listaNotasAdapter.altera ( posicao, nota );
    }

    private void adicionarNota(Nota nota) {
        this.listaNotasAdapter.adicionar ( nota );
        dao.insere ( nota );
    }

    private boolean ehResultadoAlteraNota(int requestCode, @Nullable Intent data) {
        return ehRequisicaoAlteraNota ( requestCode )
                && temNota ( data );
    }

    private boolean ehResultadoInsereNota(int requestCode, @Nullable Intent data) {
        return ehCodigoRequisicaoInsereNota ( requestCode )
                && temNota ( data );
    }

    private boolean temNota(@Nullable Intent data) {
        return data != null && data.hasExtra ( CHAVE_NOTA );
    }

    private boolean ehPosicaoValida(int posicao) {
        return posicao > -1;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private boolean ehRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private boolean ehResultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private void configRecyclerView() {
        final RecyclerView listaNotas = findViewById ( R.id.lista_notas_recyclerview );

        configAdapter ( listaNotas );

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper ( new NotaItemTouchHelperCallback ( listaNotasAdapter ) );
        itemTouchHelper.attachToRecyclerView ( listaNotas );
    }

    private RecyclerView configAdapter(RecyclerView listaNotas) {
        listaNotasAdapter = new ListaNotasAdapter ( this, dao.todos () );

        listaNotas.setAdapter ( listaNotasAdapter );

        listaNotasAdapter.setOnItemClickListener ( new OnItemClickListener () {
            @Override
            public void onItemClick(Nota nota, int position) {
                Intent intent = new Intent ( ListaNotasActivity.this, FormularioNotaActivity.class );
                intent.putExtra ( CHAVE_NOTA, nota );
                intent.putExtra ( CHAVE_POSICAO, position );
                startActivityForResult ( intent, CODIGO_REQUISICAO_ALTERA_NOTA );
            }
        } );

        return listaNotas;
    }
}