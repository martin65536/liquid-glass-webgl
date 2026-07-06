package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qm {
    public static final dt0 f;
    public static final qm g;
    public static final qm h;
    public static final qm i;
    public static final qm j;
    public static final qm k;
    public static final qm l;
    public static final /* synthetic */ qm[] m;
    public static final /* synthetic */ mr n;
    public final int e;

    static {
        qm qmVar = new qm(0, 120, "LDPI");
        g = qmVar;
        qm qmVar2 = new qm(1, 160, "MDPI");
        h = qmVar2;
        qm qmVar3 = new qm(2, 240, "HDPI");
        i = qmVar3;
        qm qmVar4 = new qm(3, 320, "XHDPI");
        j = qmVar4;
        qm qmVar5 = new qm(4, 480, "XXHDPI");
        k = qmVar5;
        qm qmVar6 = new qm(5, 640, "XXXHDPI");
        l = qmVar6;
        qm[] qmVarArr = {qmVar, qmVar2, qmVar3, qmVar4, qmVar5, qmVar6};
        m = qmVarArr;
        n = new mr(qmVarArr);
        f = new dt0(27);
    }

    public qm(int i2, int i3, String str) {
        this.e = i3;
    }

    public static qm valueOf(String str) {
        return (qm) Enum.valueOf(qm.class, str);
    }

    public static qm[] values() {
        return (qm[]) m.clone();
    }
}
