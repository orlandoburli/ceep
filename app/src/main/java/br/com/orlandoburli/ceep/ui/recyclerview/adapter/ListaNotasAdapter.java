package br.com.orlandoburli.ceep.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import br.com.orlandoburli.ceep.R;
import br.com.orlandoburli.ceep.model.Nota;
import br.com.orlandoburli.ceep.ui.activity.ListaNotasActivity;
import br.com.orlandoburli.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private final ListaNotasActivity context;
    private final List<Nota> notas;
    private OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(ListaNotasActivity context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    public void adicionar(Nota nota) {
        this.notas.add ( nota );
//        this.notifyDataSetChanged ();
        this.notifyItemInserted ( this.notas.size () - 1 );
    }

    public void altera(int posicao, Nota nota) {
        this.notas.set ( posicao, nota );
        this.notifyItemChanged ( posicao );
    }

    public void remove(int posicao) {
        this.notas.remove ( posicao );
        this.notifyItemRemoved ( posicao );
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap ( notas, posicaoInicial, posicaoFinal );
        this.notifyItemMoved ( posicaoInicial, posicaoFinal );
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.item_nota, parent, false );

        return new NotaViewHolder ( view );
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder holder, int position) {
        Nota nota = this.notas.get ( position );

        holder.bind ( nota, position );
    }

    @Override
    public int getItemCount() {
        return notas.size ();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private Nota nota;

        public NotaViewHolder(@NonNull View itemView) {
            super ( itemView );

            this.titulo = itemView.findViewById ( R.id.item_nota_titulo );
            this.descricao = itemView.findViewById ( R.id.item_nota_descricao );

            itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick ( nota, getAdapterPosition () );
                }
            } );
        }

        public void bind(Nota nota, int position) {
            this.nota = nota;

            bindFields ( nota );
        }

        private void bindFields(Nota nota) {
            titulo.setText ( nota.getTitulo () );
            descricao.setText ( nota.getDescricao () );
        }
    }
}
