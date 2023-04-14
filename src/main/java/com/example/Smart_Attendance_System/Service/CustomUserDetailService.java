package com.example.Smart_Attendance_System.Service;

import com.example.Smart_Attendance_System.Dao.StudentRepo;
import com.example.Smart_Attendance_System.Entity.Student;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {


    private final StudentRepo studentRepo;

    public CustomUserDetailService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    //userdetails is enbuild class
    public UserDetails loadUserByUsername(String enrollno) throws UsernameNotFoundException
    {
        Student stu = studentRepo.getReferenceById(Long.parseLong(enrollno));

        if (stu == null) {
            throw new UsernameNotFoundException("Invalid User");
        }
        else
        {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add( new SimpleGrantedAuthority("ROLE_USER"));

            return new org
                    .springframework
                    .security
                    .core
                    .userdetails
                    .User(stu.getEnrollno()+"", stu.getPassword(), grantedAuthorities);

        }
    }
}


