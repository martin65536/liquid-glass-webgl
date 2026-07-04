package defpackage;

import android.content.res.Configuration;
import android.os.LocaleList;
import java.util.Locale;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class li {
    public static LocaleList a(Locale... localeArr) {
        return new LocaleList(localeArr);
    }

    public static LocaleList b() {
        return LocaleList.getDefault();
    }

    public static LocaleList c(Configuration configuration) {
        return configuration.getLocales();
    }
}
