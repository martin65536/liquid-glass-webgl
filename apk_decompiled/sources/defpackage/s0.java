package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class s0 {
    public String a;
    public final int[] b = new int[2];

    public abstract int[] a(int i);

    public final int[] b(int i, int i2) {
        if (i >= 0 && i2 >= 0 && i != i2) {
            int[] iArr = this.b;
            iArr[0] = i;
            iArr[1] = i2;
            return iArr;
        }
        return null;
    }

    public final String c() {
        String str = this.a;
        if (str != null) {
            return str;
        }
        o20.G("text");
        throw null;
    }

    public abstract int[] d(int i);
}
