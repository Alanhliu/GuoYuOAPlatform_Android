package com.guoyu.guoyuoaplatform_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.guoyu.guoyuoaplatform_android.activity.CountryActivity;
import com.guoyu.guoyuoaplatform_android.R;
import com.guoyu.guoyuoaplatform_android.customview.ContentView;
import com.guoyu.guoyuoaplatform_android.customview.NavigationView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnnouncementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnnouncementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnnouncementFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int IMAGE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private NavigationView navigationView;

    private ImageView navigation_right_iv;

    private ContentView linearLayout0;
    private ContentView linearLayout1;
    private ContentView linearLayout2;
    private ContentView linearLayout3;
    private ContentView linearLayout4;
    private ContentView linearLayout5;
    private ContentView linearLayout6;

    private OnFragmentInteractionListener mListener;

    public AnnouncementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnnouncementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnnouncementFragment newInstance(String param1, String param2) {
        AnnouncementFragment fragment = new AnnouncementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();

        navigationView = (NavigationView) activity.findViewById(R.id.navigation_annnouncment);
        navigationView.setClickCallback(new NavigationView.ClickCallback() {
            @Override
            public void onLeftClick() {
                Toast.makeText(getActivity().getApplicationContext(),"click left",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightClick() {
                Toast.makeText(getActivity().getApplicationContext(),"click right",Toast.LENGTH_SHORT).show();
            }
        });

        linearLayout0 = (ContentView)activity.findViewById(R.id.contentview_0);
        linearLayout1 = (ContentView)activity.findViewById(R.id.contentview_1);
        linearLayout2 = (ContentView)activity.findViewById(R.id.contentview_2);
        linearLayout3 = (ContentView)activity.findViewById(R.id.contentview_3);
        linearLayout4 = (ContentView)activity.findViewById(R.id.contentview_4);
        linearLayout5 = (ContentView)activity.findViewById(R.id.contentview_5);
        linearLayout6 = (ContentView)activity.findViewById(R.id.contentview_6);

        linearLayout0.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        linearLayout5.setOnClickListener(this);
        linearLayout6.setOnClickListener(this);

        navigation_right_iv = (ImageView) activity.findViewById(R.id.navigation_annnouncment).findViewById(R.id.iv_nav_right);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.contentview_0:

                intent = new Intent(this.getActivity(), CountryActivity.class);
                this.startActivity(intent);

                break;
            case R.id.contentview_1:

                break;
            case R.id.contentview_2:
                break;
            case R.id.contentview_3:
                break;
            case R.id.contentview_4:
                break;
            case R.id.contentview_5:
                break;
            case R.id.contentview_6:
                break;
            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
