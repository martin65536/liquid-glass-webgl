package defpackage;

import android.os.LocaleList;
import java.util.Locale;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ya0 implements xa0 {
    public final LocaleList a;

    public ya0(Object obj) {
        this.a = x0.d(obj);
    }

    @Override // defpackage.xa0
    public final Object a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        boolean equals;
        equals = this.a.equals(((xa0) obj).a());
        return equals;
    }

    @Override // defpackage.xa0
    public final Locale get(int i) {
        Locale locale;
        locale = this.a.get(i);
        return locale;
    }

    public final int hashCode() {
        int hashCode;
        hashCode = this.a.hashCode();
        return hashCode;
    }

    @Override // defpackage.xa0
    public final boolean isEmpty() {
        boolean isEmpty;
        isEmpty = this.a.isEmpty();
        return isEmpty;
    }

    @Override // defpackage.xa0
    public final int size() {
        int size;
        size = this.a.size();
        return size;
    }

    public final String toString() {
        String localeList;
        localeList = this.a.toString();
        return localeList;
    }
}
