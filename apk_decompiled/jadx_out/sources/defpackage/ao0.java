package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ao0 {
    public static final ao0 b = new ao0(new he(0.0f));
    public final he a;

    public ao0(he heVar) {
        this.a = heVar;
        if (!Float.isNaN(0.0f)) {
            return;
        }
        v7.m("current must not be NaN");
        throw null;
    }

    public final he a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof ao0) && this.a.equals(((ao0) obj).a)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (this.a.hashCode() + (Float.floatToIntBits(0.0f) * 31)) * 31;
    }

    public final String toString() {
        return "ProgressBarRangeInfo(current=0.0, range=" + this.a + ", steps=0)";
    }
}
