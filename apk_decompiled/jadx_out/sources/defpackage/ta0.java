package defpackage;

import java.util.Locale;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ta0 {
    public final Locale a;

    public ta0(Locale locale) {
        this.a = locale;
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ta0)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return o20.e(this.a.toLanguageTag(), ((ta0) obj).a.toLanguageTag());
    }

    public final int hashCode() {
        return this.a.toLanguageTag().hashCode();
    }

    public final String toString() {
        return this.a.toLanguageTag();
    }
}
