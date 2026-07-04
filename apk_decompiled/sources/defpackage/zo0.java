package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zo0 implements lr0 {
    public static final zo0 a = new Object();

    @Override // defpackage.lr0
    public final kr0 a(long j, m40 m40Var, np npVar) {
        m40Var.getClass();
        npVar.getClass();
        return new kr0(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override // defpackage.zv0
    public final g30 b(long j, m40 m40Var, mm mmVar) {
        m40Var.getClass();
        mmVar.getClass();
        return new gj0(new wo0(0.0f, 0.0f, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L))));
    }

    public final boolean equals(Object obj) {
        if (this == obj || (obj instanceof zo0)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return -616806923;
    }

    public final String toString() {
        return "Rectangle";
    }
}
