package defpackage;

import java.util.Comparator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class b60 implements Comparator {
    public final /* synthetic */ int a;
    public final /* synthetic */ p5 b;

    public /* synthetic */ b60(p5 p5Var, int i) {
        this.a = i;
        this.b = p5Var;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int i = this.a;
        p5 p5Var = this.b;
        switch (i) {
            case 0:
                return n20.m(Integer.valueOf(p5Var.c(((i70) obj).g)), Integer.valueOf(p5Var.c(((i70) obj2).g)));
            case 1:
                return n20.m(Integer.valueOf(p5Var.c(((i70) obj).g)), Integer.valueOf(p5Var.c(((i70) obj2).g)));
            case 2:
                return n20.m(Integer.valueOf(p5Var.c(((i70) obj2).g)), Integer.valueOf(p5Var.c(((i70) obj).g)));
            default:
                return n20.m(Integer.valueOf(p5Var.c(((i70) obj2).g)), Integer.valueOf(p5Var.c(((i70) obj).g)));
        }
    }
}
