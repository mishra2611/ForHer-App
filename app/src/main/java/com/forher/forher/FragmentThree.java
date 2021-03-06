package com.forher.forher;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.Vector;

import dmax.dialog.SpotsDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment {
    private RecyclerView founderRecyclerView;
    private RecyclerView.Adapter founderViewAdapter;
    private RecyclerView.LayoutManager founderViewLayoutManager;
    FounderDataset founderDataset=new FounderDataset("Loading...","Loading...","Loading...","Loading...","Loading...","Loading...","Loading...");
    Vector<FounderDataset> founderVector=new Vector<FounderDataset>();
    public SpotsDialog cardProgressDialog;
    JSONArray founderArray=null;
    //cofounder declarations
    private RecyclerView cofounderRecyclerView;
    private RecyclerView.Adapter cofounderViewAdapter;
    private RecyclerView.LayoutManager cofounderViewLayoutManager;
    CoFounderDataset cofounderDataset=new CoFounderDataset("Loading...","Loading...","Loading...","Loading...","Loading...","Loading...","Loading...");
    Vector<CoFounderDataset> cofounderVector=new Vector<CoFounderDataset>();

    JSONArray cofounderArray=null;
    //end of declaration
    //Board declarations
    private RecyclerView boardRecyclerView;
    private RecyclerView.Adapter boardViewAdapter;
    private RecyclerView.LayoutManager boardViewLayoutManager;
    BoardDataset boardDataset=new BoardDataset("Loading...","Loading...","Loading...","Loading...","Loading...","Loading...","Loading...");
    Vector<BoardDataset> boardVector=new Vector<BoardDataset>();

    JSONArray boardArray=null;
    //end of declaration
    public FragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_three, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        founderVector.add(founderDataset);
        founderRecyclerView=(RecyclerView) getView().findViewById(R.id.founder_recycler_view);
        founderViewLayoutManager=new org.solovyev.android.views.llm.LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        founderRecyclerView.setLayoutManager(founderViewLayoutManager);
        founderViewAdapter=new FounderAdapter(founderVector);
        founderRecyclerView.setAdapter(founderViewAdapter);
        founderRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        //initialization of cofounder elements
        cofounderVector.add(cofounderDataset);
        cofounderRecyclerView=(RecyclerView) getView().findViewById(R.id.cofounder_recycler_view);
        cofounderViewLayoutManager=new org.solovyev.android.views.llm.LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cofounderRecyclerView.setLayoutManager(cofounderViewLayoutManager);
        cofounderViewAdapter=new CoFounderAdapter(cofounderVector);
        cofounderRecyclerView.setAdapter(cofounderViewAdapter);
        cofounderRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        //end of code
        //initialization of board elements
        boardVector.add(boardDataset);
        boardRecyclerView=(RecyclerView) getView().findViewById(R.id.board_recycler_view);
        boardViewLayoutManager=new org.solovyev.android.views.llm.LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        boardRecyclerView.setLayoutManager(boardViewLayoutManager);
        boardViewAdapter=new BoardAdapter(boardVector);
        boardRecyclerView.setAdapter(boardViewAdapter);
        boardRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        //end of code
        //cardProgressDialog= ProgressDialog.show(getActivity(), "Loading", "Values loading", true, false);
        cardProgressDialog=new SpotsDialog(getActivity(),R.style.Custom);
        cardProgressDialog.show();
        getDataFromServer();
    }


    public void getDataFromServer(){
        System.out.println("getDataFromServer called");

        RequestQueue requestQueue=VolleySingletonCall.getVolleyInstance().getRequestQueue();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, getString(R.string.ip)+"/api/v1.0/team", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("Event:getDataFromServer:Response recieved");
                Toast.makeText(getActivity(), "response recieved", Toast.LENGTH_SHORT).show();
                try
                {

                    if(response !=null)
                    {
                        JSONObject jsonObj=new JSONObject(response);
                        //founderArray=jsonObj.getJSONArray("founder");
                         founderVector.clear();
                        cofounderVector.clear();
                        //for(int i=0;i<founderArray.length();i++)
                        //{
                           JSONObject c=jsonObj.getJSONObject("founder");
                            String name=c.getString("name");
                            String about_short=c.getString("about_short");
                            String img=c.getString("image");
                            JSONObject foundersocial=c.getJSONObject("social");
                            String cfblink=foundersocial.getString("facebook");
                            String fgplus=foundersocial.getString("google");
                            String ftwitter=foundersocial.getString("twitter");
                            String flinkedin=foundersocial.getString("linkedin");
                            System.out.println("body is :"+about_short);
                           founderDataset=new FounderDataset(name,about_short,cfblink,fgplus,ftwitter,flinkedin,img);
                            founderVector.add(founderDataset);


                        //}
                        JSONArray cf=jsonObj.getJSONArray("cofounders");
                        for(int i=0;i<cf.length();i++)
                        {
                            JSONObject cofounderobj=cf.getJSONObject(i);
                            String cofname=cofounderobj.getString("name");
                            String cofabout=cofounderobj.getString("about_short");
                            String cfimg=c.getString("image");
                            JSONObject social=cofounderobj.getJSONObject("social");
                            String fblink=social.getString("facebook");
                            String gpluslink=social.getString("google");
                            String twitter=social.getString("twitter");
                            String linkedin=social.getString("linkedin");
                            cofounderDataset=new CoFounderDataset(cofname,cofabout,fblink,gpluslink,twitter,linkedin,cfimg);
                            cofounderVector.add(cofounderDataset);
                        }
                        //code for getting board members
                        boardVector.clear();
                        JSONArray bm=jsonObj.getJSONArray("board");

                        for(int i=0;i<bm.length();i++)
                        {
                            JSONObject boardobj=bm.getJSONObject(i);
                            String bmname=boardobj.getString("name");
                            String bmabout=boardobj.getString("about_short");
                            String cfimg=boardobj.getString("image");
                            JSONObject social=boardobj.getJSONObject("social");
                            String fblink=social.getString("facebook");
                            String gpluslink=social.getString("google");
                            String twitter=social.getString("twitter");
                            String linkedin=social.getString("linkedin");
                            boardDataset=new BoardDataset(bmname,bmabout,fblink,gpluslink,twitter,linkedin,cfimg);
                            boardVector.add(boardDataset);
                        }
                        //end of code

                        founderViewAdapter.notifyDataSetChanged();
                        cofounderViewAdapter.notifyDataSetChanged();
                        boardViewAdapter.notifyDataSetChanged();
                        cardProgressDialog.dismiss();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"response error",Toast.LENGTH_SHORT).show();
                cardProgressDialog.dismiss();
                System.out.println("error is: "+error);

            }
        });
        requestQueue.add(stringRequest);

    }

    class FounderDataset{
        String name;
        String about_short;
        String fblink;
        String gpluslink;
        String twitterlink;
        String linkedinlink;
        String imagelink;
        FounderDataset(String name,String about_short,String fblink,String gpluslink,String twitterlink,String linkedinlink,String imagelink){
            this.name=name;
            this.about_short=about_short;
            this.fblink=fblink;
            this.gpluslink=gpluslink;
            this.twitterlink=twitterlink;
            this.linkedinlink=linkedinlink;
            this.imagelink=imagelink;
        }
    }

    class CoFounderDataset{
        String name;
        String about_short;
        String fblink;
        String gpluslink;
        String twitterlink;
        String linkedinlink;
        String imagelink;
        CoFounderDataset(String name,String about_short,String fblink,String gpluslink,String twitterlink,String linkedinlink,String imagelink){
            this.name=name;
            this.about_short=about_short;
            this.fblink=fblink;
            this.gpluslink=gpluslink;
            this.twitterlink=twitterlink;
            this.linkedinlink=linkedinlink;
            this.imagelink=imagelink;
        }
    }

    class BoardDataset{
       String name;
        String about_short;
        String fblink;
        String gpluslink;
        String twitterlink;
        String linkedinlink;
        String imagelink;
        BoardDataset(String name,String about_short,String fblink,String gpluslink,String twitterlink,String linkedinlink,String imagelink){
            this.name=name;
            this.about_short=about_short;
            this.fblink=fblink;
            this.gpluslink=gpluslink;
            this.twitterlink=twitterlink;
            this.linkedinlink=linkedinlink;
            this.imagelink=imagelink;
        }
    }


    public class CoFounderAdapter extends RecyclerView.Adapter<CoFounderAdapter.ViewHolder>{
        public Vector<CoFounderDataset> cofounderVector=new Vector<CoFounderDataset>();
        public CoFounderAdapter(Vector<CoFounderDataset> cofounderVector) {
            this.cofounderVector=cofounderVector;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.founder_layout,parent,false);
            ViewHolder vh=new ViewHolder( v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final int pos=position;
            holder.nameLoc.setText(cofounderVector.elementAt(position).name);
            //holder.imageLoc.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
            holder.about_short_Loc.setText(cofounderVector.elementAt(position).about_short);
            holder.fb_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            Picasso.with(getActivity()).load(getString(R.string.ip)+cofounderVector.elementAt(position).imagelink).into((ImageView) holder.imgView);
            holder.fb_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = cofounderVector.elementAt(pos).fblink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
            holder.gplus_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.gplus_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = cofounderVector.elementAt(pos).gpluslink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
            holder.twitter_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.twitter_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = cofounderVector.elementAt(pos).twitterlink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
            holder.linkedin_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.linkedin_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String link = cofounderVector.elementAt(pos).linkedinlink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
        }



        @Override
        public int getItemCount() {
            return cofounderVector.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView nameLoc;
            public TextView about_short_Loc;
            public TextView fb_link;
            public TextView gplus_link;
            public TextView twitter_link;
            public TextView linkedin_link;
            public ImageView imgView;
            public ViewHolder(View itemView) {
                super(itemView);
                nameLoc=(TextView)itemView.findViewById(R.id.founder_name);
                about_short_Loc=(TextView)itemView.findViewById(R.id.founder_about_short);
                fb_link=(TextView)itemView.findViewById(R.id.fb_link);
                gplus_link=(TextView)itemView.findViewById(R.id.gplus_link);
                twitter_link=(TextView)itemView.findViewById(R.id.twitter_link);
                linkedin_link=(TextView)itemView.findViewById(R.id.linkedin_link);
                imgView=(ImageView)itemView.findViewById(R.id.profile_img);
            }
        }
    }


    public class FounderAdapter extends RecyclerView.Adapter<FounderAdapter.ViewHolder>{
        public Vector<FounderDataset> founderVector=new Vector<FounderDataset>();
        public FounderAdapter(Vector<FounderDataset> founderVector) {
            this.founderVector=founderVector;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.founder_layout,parent,false);
            ViewHolder vh=new ViewHolder( v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final int pos=position;
            holder.nameLoc.setText(founderVector.elementAt(position).name);
            //holder.imageLoc.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
            holder.about_short_Loc.setText(founderVector.elementAt(position).about_short);
            Picasso.with(getActivity()).load(getString(R.string.ip)+founderVector.elementAt(position).imagelink).into((ImageView) holder.imgView);
            holder.fb_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.fb_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = founderVector.elementAt(pos).fblink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
            holder.gplus_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.gplus_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = founderVector.elementAt(pos).gpluslink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
            holder.twitter_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.twitter_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = founderVector.elementAt(pos).twitterlink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
            holder.linkedin_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.linkedin_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = founderVector.elementAt(pos).linkedinlink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
        }



        @Override
        public int getItemCount() {
            return founderVector.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView nameLoc;
            public TextView about_short_Loc;
            public TextView fb_link;
            public TextView gplus_link;
            public TextView twitter_link;
            public TextView linkedin_link;
            public ImageView imgView;
            public ViewHolder(View itemView) {
                super(itemView);
                nameLoc=(TextView)itemView.findViewById(R.id.founder_name);
                about_short_Loc=(TextView)itemView.findViewById(R.id.founder_about_short);
                fb_link=(TextView)itemView.findViewById(R.id.fb_link);
                gplus_link=(TextView)itemView.findViewById(R.id.gplus_link);
                twitter_link=(TextView)itemView.findViewById(R.id.twitter_link);
                linkedin_link=(TextView)itemView.findViewById(R.id.linkedin_link);
                imgView=(ImageView)itemView.findViewById(R.id.profile_img);
            }
        }
    }


    public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>{
        public Vector<BoardDataset> boardVector=new Vector<BoardDataset>();
        public BoardAdapter(Vector<BoardDataset> boardVector) {
            this.boardVector=boardVector;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.founder_layout,parent,false);
            ViewHolder vh=new ViewHolder( v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final int pos=position;
            holder.nameLoc.setText(boardVector.elementAt(position).name);
            //holder.imageLoc.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
            holder.about_short_Loc.setText(boardVector.elementAt(position).about_short);
            Picasso.with(getActivity()).load(getString(R.string.ip)+boardVector.elementAt(position).imagelink).into((ImageView) holder.imgView);
            holder.fb_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));

            holder.fb_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = boardVector.elementAt(pos).fblink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
            holder.gplus_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.gplus_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = boardVector.elementAt(pos).gpluslink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });
            holder.twitter_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.twitter_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = boardVector.elementAt(pos).twitterlink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });

            holder.linkedin_link.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
            holder.linkedin_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = boardVector.elementAt(pos).linkedinlink.toString();
                    Intent i = new Intent(getActivity(), EventCardExpand.class);
                    i.putExtra("KEY", pos);
                    i.putExtra("FROM", "TEAM");
                    i.putExtra("LINK", link);
                    startActivity(i);
                }
            });

        }



        @Override
        public int getItemCount() {
            return boardVector.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView nameLoc;
            public TextView about_short_Loc;
            public TextView fb_link;
            public TextView gplus_link;
            public TextView twitter_link;
            public TextView linkedin_link;
            public ImageView imgView;
            public ViewHolder(View itemView) {
                super(itemView);
                nameLoc=(TextView)itemView.findViewById(R.id.founder_name);
                about_short_Loc=(TextView)itemView.findViewById(R.id.founder_about_short);
                fb_link=(TextView)itemView.findViewById(R.id.fb_link);
                gplus_link=(TextView)itemView.findViewById(R.id.gplus_link);
                twitter_link=(TextView)itemView.findViewById(R.id.twitter_link);
                linkedin_link=(TextView)itemView.findViewById(R.id.linkedin_link);
                imgView=(ImageView)itemView.findViewById(R.id.profile_img);
            }
        }
    }
}
