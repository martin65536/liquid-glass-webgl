package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zk0 extends fl0 {
    public final float c;
    public final float d;

    public zk0(float f, float f2) {
        super(3);
        this.c = f;
        this.d = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zk0)) {
            return false;
        }
        zk0 zk0Var = (zk0) obj;
        if (Float.compare(this.c, zk0Var.c) == 0 && Float.compare(this.d, zk0Var.d) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.d) + (Float.floatToIntBits(this.c) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RelativeMoveTo(dx=");
        sb.append(this.c);
        sb.append(", dy=");
        return d3.v(sb, this.d, ')');
    }
}
