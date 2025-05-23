package abl.frd.qremit.converter.helper;

import abl.frd.qremit.converter.model.Role;
import abl.frd.qremit.converter.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyUserDetails implements UserDetails {
    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }
    
    public String getLoginId() {
        return user.getLoginId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }
    public Set<Role> getRolesWithIds() {
        return user.getRoles();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    public boolean hasRole(String roleName){
        Collection<? extends GrantedAuthority> authoritiesList = getAuthorities();
        if(authoritiesList.toString().contains(roleName)){
            return true;
        }
        else
            return false;
    }
    @Override
    public boolean isEnabled() {
        return user.getActiveStatus();
    }

    public String getUserEmail(){
        return this.user.getUserEmail();
    }
    public String getUserExchangeCode(){
        return this.user.getExchangeCode();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public boolean isPasswordChangeRequired() {
        return user.isPasswordChangeRequired();
    }

    public int getFailedAttempt(){
        return user.getFailedAttempt();
    }
    public void setFailedAttempt(int failedAttempt) {
        this.user.setFailedAttempt(failedAttempt);
    }
}
