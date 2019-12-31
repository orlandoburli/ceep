package br.com.orlandoburli.ceep.ui.recyclerview.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.orlandoburli.ceep.R;
import br.com.orlandoburli.ceep.model.Nota;
import br.com.orlandoburli.ceep.ui.activity.ListaNotasActivity;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private final ListaNotasActivity context;
    private final List<Nota> notas;

    public ListaNotasAdapter(ListaNotasActivity context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    public void adicionar(Nota nota) {
        this.notas.add ( nota );
        this.notifyDataSetChanged ();
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.item_nota, parent, false );

        return new NotaViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder holder, int position) {
        Nota nota = this.notas.get ( position );

        holder.bind ( nota );
    }

    @Override
    public int getItemCount() {
        return notas.size ();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;

        public NotaViewHolder(@NonNull View itemView) {
            super ( itemView );

            this.titulo = itemView.findViewById ( R.id.item_nota_titulo );
            this.descricao = itemView.findViewById ( R.id.item_nota_descricao );
        }

        public void bind(Nota nota) {
            bindFields ( nota );
        }

        private void bindFields(Nota nota) {
            titulo.setText ( nota.getTitulo () );
            descricao.setText ( nota.getDescricao () );
        }
    }
}
