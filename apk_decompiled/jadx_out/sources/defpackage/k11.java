package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k11 {
    public final int a;

    public final boolean equals(Object obj) {
        if (obj instanceof k11) {
            if (this.a != ((k11) obj).a) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.a;
    }

    public final String toString() {
        int i = this.a;
        if (i == 1) {
            return "Linearity.Linear";
        }
        if (i == 2) {
            return "Linearity.FontHinting";
        }
        if (i == 3) {
            return "Linearity.None";
        }
        return "Invalid";
    }
}
