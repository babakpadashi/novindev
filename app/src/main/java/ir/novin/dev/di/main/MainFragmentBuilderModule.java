package ir.novin.dev.di.main;




import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ir.novin.dev.ui.main.users.UsersFragment;

@Module
public abstract class MainFragmentBuilderModule {

   @ContributesAndroidInjector
   abstract UsersFragment contributesUsersFragment();



}
