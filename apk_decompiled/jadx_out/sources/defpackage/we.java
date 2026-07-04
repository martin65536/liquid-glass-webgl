package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class we {
    public final String a;
    public final long b;
    public final int c;

    public we(String str, long j, int i) {
        this.a = str;
        this.b = j;
        this.c = i;
        if (str.length() != 0) {
            if (i >= -1 && i <= 63) {
                return;
            }
            v7.m("The id must be between -1 and 63");
            throw null;
        }
        v7.m("The name of a color space cannot be null and must contain at least 1 character");
        throw null;
    }

    public abstract float a(int i);

    public abstract float b(int i);

    public boolean c() {
        return false;
    }

    public abstract long d(float f, float f2, float f3);

    public abstract float e(float f, float f2, float f3);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            we weVar = (we) obj;
            if (this.c == weVar.c && this.a.equals(weVar.a)) {
                return k81.q(this.b, weVar.b);
            }
            return false;
        }
        return false;
    }

    public abstract long f(float f, float f2, float f3, float f4, we weVar);

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        long j = this.b;
        return ((hashCode + ((int) (j ^ (j >>> 32)))) * 31) + this.c;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(" (id=");
        sb.append(this.c);
        sb.append(", model=");
        long j = this.b;
        if (k81.q(j, 12884901888L)) {
            str = "Rgb";
        } else if (k81.q(j, 12884901889L)) {
            str = "Xyz";
        } else if (k81.q(j, 12884901890L)) {
            str = "Lab";
        } else if (k81.q(j, 17179869187L)) {
            str = "Cmyk";
        } else {
            str = "Unknown";
        }
        sb.append((Object) str);
        sb.append(')');
        return sb.toString();
    }
}
