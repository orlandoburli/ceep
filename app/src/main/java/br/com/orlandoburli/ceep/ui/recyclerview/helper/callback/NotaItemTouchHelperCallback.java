package br.com.orlandoburli.ceep.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.orlandoburli.ceep.dao.NotaDAO;
import br.com.orlandoburli.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private NotaDAO dao = new NotaDAO ();
    private ListaNotasAdapter adapter;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeArrastar = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int marcacoesDeDeslize  = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        return makeMovementFlags ( marcacoesDeArrastar, marcacoesDeDeslize );
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition ();
        int posicaoFinal   = target.getAdapterPosition ();

        trocaNotas ( posicaoInicial, posicaoFinal );

        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        removeNota ( viewHolder.getAdapterPosition () );
    }

    private void removeNota(int posicao) {
        dao.remove ( posicao );

        adapter.remove ( posicao );
    }

    private void trocaNotas(int posicaoInicial, int posicaoFinal) {
        dao.troca ( posicaoInicial, posicaoFinal );

        adapter.troca ( posicaoInicial, posicaoFinal );
    }
}
