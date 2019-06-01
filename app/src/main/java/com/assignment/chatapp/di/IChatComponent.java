package com.assignment.chatapp.di;

import com.assignment.chatapp.component.ApplicationComponent;
import com.assignment.chatapp.scope.UserScope;
import com.assignment.chatapp.viewmodel.ChatViewModel;

import dagger.Component;

@UserScope
@Component(dependencies = ApplicationComponent.class, modules = IChatModule.class)
public interface IChatComponent {

    void inject(ChatViewModel chatViewModel);
}
