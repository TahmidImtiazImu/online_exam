package com.example.online_exam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText regUser, regEmail, regPass, regConfirmPass, regInstitution, regFullName;
    private Button signUpBtn, backToLoginBtn;
    private RadioGroup gender;
    private RadioButton maleBtn, femaleBtn;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeacherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherFragment newInstance(String param1, String param2) {
        TeacherFragment fragment = new TeacherFragment();
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


    //new OnCreateMethod

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view = null;
        view = inflater.inflate(R.layout.activity_fragment_teacher, container, false);
        regFullName = view.findViewById(R.id.regName);
        regUser = view.findViewById(R.id.regUser);
        regEmail = view.findViewById(R.id.regEmail);
        regPass = view.findViewById(R.id.regPassword);
        regConfirmPass = view.findViewById(R.id.regConfirmPassword);
        regInstitution = view.findViewById(R.id.regInstitution);
        gender = view.findViewById(R.id.male_female);
        signUpBtn = view.findViewById(R.id.btnSignUpTeacher);
        backToLoginBtn = view.findViewById(R.id.backToLogin);
        maleBtn = view.findViewById(R.id.male);
        femaleBtn = view.findViewById(R.id.female);
        progressBar = view.findViewById(R.id.simpleProgressBar);


        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Sign Up Button Working");
                createUser();
            }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

    private void createUser() {
        String userName = regUser.getText().toString();
        String fullName = regFullName.getText().toString();
        String email = regEmail.getText().toString();
        String pass = regPass.getText().toString();
        String confirmPass = regConfirmPass.getText().toString();
        String institution = regInstitution.getText().toString();
        String male = maleBtn.getText().toString();
        String female = femaleBtn.getText().toString();

//        if ((!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
//            if (!pass.isEmpty()) {
//                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                        Toast.makeText( getContext(), "Registered Succesfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent( getContext(), LoginActivity.class));
//                        getActivity().finish();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull @NotNull Exception e) {
//                        Toast.makeText(getContext(), "Registration Error", Toast.LENGTH_SHORT);
//                    }
//                });
//            } else {
//                regPass.setError("Empty Fields are not allowed");
//            }
//        } else if (email.isEmpty()) {
//            regEmail.setError("Empty Fields are not allowed");
//        }

        if(fullName.isEmpty()){
            regFullName.setError("FullName is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userName.isEmpty()){
            regUser.setError("UserName is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(email.isEmpty()){
            regEmail.setError("Email is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.isEmpty()){
            regPass.setError("Password is Required");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(confirmPass.isEmpty()){
            regConfirmPass.setError("Retype the Password");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pass.equals(confirmPass)){
            regConfirmPass.setError("Password doesn't match");
            Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!maleBtn.isChecked() && !femaleBtn.isChecked()){
            Toast.makeText(getContext(), "Please Select your gender", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(getView().VISIBLE); //maybe smth wrong

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkuser = reference.orderByChild("Username").equalTo(userName);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    regUser.setError("This username is already taken");
                    return;
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "User Created", Toast.LENGTH_SHORT).show();
                            //userid = Objects.requireNonNull(fbase.getCurrentUser()).getUid() ;
                            HashMap<String,Object> usermap = new HashMap<>() ;

                            usermap.put("Role", "Teacher"); //ekhane matobbori korsi
                            usermap.put("Username",userName) ;
                            usermap.put("Enter_name", fullName);
                            usermap.put("Email",email) ;
                            usermap.put("Password",pass) ;
                            usermap.put("Institution", institution);
                            if(maleBtn.isChecked()) usermap.put("Gender",male) ;
                            if(femaleBtn.isChecked()) usermap.put("Gender",female) ;

                            reference.child(userName).setValue(usermap);


                            startActivity(new Intent(getContext(),LoginActivity.class));
                        }
                        else {
                            Toast.makeText(getContext(), "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}


