package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nu implements Comparable {
    public static final nu f;
    public static final nu g;
    public static final nu h;
    public final int e;

    static {
        nu nuVar = new nu(100);
        nu nuVar2 = new nu(200);
        nu nuVar3 = new nu(300);
        nu nuVar4 = new nu(400);
        nu nuVar5 = new nu(500);
        nu nuVar6 = new nu(600);
        f = nuVar6;
        nu nuVar7 = new nu(700);
        nu nuVar8 = new nu(800);
        nu nuVar9 = new nu(900);
        g = nuVar4;
        h = nuVar5;
        jc0.w(nuVar, nuVar2, nuVar3, nuVar4, nuVar5, nuVar6, nuVar7, nuVar8, nuVar9);
    }

    public nu(int i) {
        this.e = i;
        boolean z = false;
        if (1 <= i && i < 1001) {
            z = true;
        }
        if (!z) {
            r00.a("Font weight can be in range [1, 1000]. Current value: " + i);
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return o20.i(this.e, ((nu) obj).e);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof nu)) {
            return false;
        }
        if (this.e == ((nu) obj).e) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.e;
    }

    public final String toString() {
        return "FontWeight(weight=" + this.e + ')';
    }
}
