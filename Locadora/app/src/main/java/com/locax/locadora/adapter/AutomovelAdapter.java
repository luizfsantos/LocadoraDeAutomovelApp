package com.locax.locadora.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.locax.locadora.R;
import com.locax.locadora.model.Automovel;
import com.locax.locadora.util.Util;
import com.locax.locadora.util.ViewHolder;

import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;


public class AutomovelAdapter extends BaseAdapter implements SectionIndexer {
    Activity context;
    Automovel[] automoveis;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public AutomovelAdapter(Activity context, Automovel[] automoveis){
        this.context = context;
        this.automoveis = automoveis;
        sectionHeaders = SectionIndexBuilder.BuildSectionHeaders(automoveis);
        positionForSectionMap = SectionIndexBuilder.BuildPositionForSectionMap(automoveis);
        sectionForPositionMap = SectionIndexBuilder.BuildSectionForPositionMap(automoveis);

    }
    @Override
    public int getCount() {
        return automoveis.length;
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0 && position < automoveis.length)
            return automoveis[position];
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //o list view recicla os layouts para melhor performance
        //o layout reciclado vem no parametro convert view
        View view = convertView;
        //se nao recebeu um layout para reutilizar deve inflar um
        if(view == null) {
            //um inflater transforma um layout em uma view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_automovel, parent, false);

            ImageView fotoAutomovel = (ImageView)view.findViewById(R.id.fotoAutomovelImageView);
            TextView modeloAutomovel = (TextView)view.findViewById(R.id.modeloAutomovelTextView);
            TextView detalhesAutomovel = (TextView)view.findViewById(R.id.detalhesAutomovelTextView);
            //faz cache dos widgets instanciados na tag da view para reusar quando houver reciclagem
            view.setTag(new ViewHolder(fotoAutomovel, modeloAutomovel, detalhesAutomovel));
        }
        //usa os widgets cacheados na view reciclada
        ViewHolder holder = (ViewHolder)view.getTag();
        //carrega os novos valores
        Drawable drawable = Util.getDrawable(context, automoveis[position].getNomeImagem());
        holder.getFotoAutomovel().setImageDrawable(drawable);
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        holder.getModeloAutomovel().setText(automoveis[position].getModelo());
        holder.getDetalhesAutomovel().setText(automoveis[position].getFabricante());

        return view;
    }
//metodos da interface SectionIndexer


    @Override
    public Object[] getSections() {
        return sectionHeaders;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionForSectionMap.get(sectionIndex).intValue();
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionForPositionMap.get(position).intValue();
    }
}
