package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yi {
    public final jb a;
    public final pc b;

    public yi(jb jbVar, pc pcVar) {
        this.a = jbVar;
        this.b = pcVar;
    }

    public final String toString() {
        pc pcVar = this.b;
        if (pcVar.i.j(dk.f) == null) {
            StringBuilder sb = new StringBuilder("Request@");
            int hashCode = hashCode();
            k81.m(16);
            String num = Integer.toString(hashCode, 16);
            num.getClass();
            sb.append(num);
            sb.append("(currentBounds()=");
            sb.append(this.a.a());
            sb.append(", continuation=");
            sb.append(pcVar);
            sb.append(')');
            return sb.toString();
        }
        v7.d();
        return null;
    }
}
