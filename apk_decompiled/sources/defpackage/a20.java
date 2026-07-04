package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a20 {
    public int a = 0;

    public final String toString() {
        StringBuilder sb = new StringBuilder("IntRef(element = ");
        sb.append(this.a);
        sb.append(")@");
        int hashCode = hashCode();
        k81.m(16);
        String num = Integer.toString(hashCode, 16);
        num.getClass();
        sb.append(num);
        return sb.toString();
    }
}
