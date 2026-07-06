package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class fl0 {
    public final boolean a;
    public final boolean b;

    public fl0(int i) {
        boolean z;
        if ((i & 1) != 0) {
            z = false;
        } else {
            z = true;
        }
        boolean z2 = (i & 2) == 0;
        this.a = z;
        this.b = z2;
    }
}
