package ir.novin.dev.database;

import android.app.Application;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ir.novin.dev.modules.Login;


public class OrmLite extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "db";
    private static final int DATABASE_VERSION = 1;


    public OrmLite(Application application) {
        super(application, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {


            TableUtils.createTable(connectionSource, Login.class);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {


        //TableUtils.dropTable(connectionSource, Person.class, false);
        //TableUtils.dropTable(connectionSource, Admin.class, false);
        onCreate(database, connectionSource);

    }

}