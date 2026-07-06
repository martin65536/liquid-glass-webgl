package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yj0 {
    public final t5 a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    public final float f;
    public final float g;

    public yj0(t5 t5Var, int i, int i2, int i3, int i4, float f, float f2) {
        this.a = t5Var;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.f = f;
        this.g = f2;
    }

    public final int a(int i) {
        int i2 = this.c;
        int i3 = this.b;
        return n30.j(i, i3, i2) - i3;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof yj0) {
                yj0 yj0Var = (yj0) obj;
                if (this.a == yj0Var.a && this.b == yj0Var.b && this.c == yj0Var.c && this.d == yj0Var.d && this.e == yj0Var.e && Float.compare(this.f, yj0Var.f) == 0 && Float.compare(this.g, yj0Var.g) == 0) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.g) + d3.s(this.f, ((((((((this.a.hashCode() * 31) + this.b) * 31) + this.c) * 31) + this.d) * 31) + this.e) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ParagraphInfo(paragraph=");
        sb.append(this.a);
        sb.append(", startIndex=");
        sb.append(this.b);
        sb.append(", endIndex=");
        sb.append(this.c);
        sb.append(", startLineIndex=");
        sb.append(this.d);
        sb.append(", endLineIndex=");
        sb.append(this.e);
        sb.append(", top=");
        sb.append(this.f);
        sb.append(", bottom=");
        return d3.v(sb, this.g, ')');
    }
}
