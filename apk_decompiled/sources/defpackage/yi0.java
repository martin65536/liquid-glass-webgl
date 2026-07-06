package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class yi0 {
    public final int a;
    public final int b;

    public /* synthetic */ yi0(int i, int i2, int i3) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2);
    }

    public abstract void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var);

    public wv b(aj0 aj0Var) {
        return null;
    }

    public final String toString() {
        String b = fp0.a(getClass()).b();
        if (b == null) {
            return "";
        }
        return b;
    }

    public yi0(int i, int i2) {
        this.a = i;
        this.b = i2;
    }
}
