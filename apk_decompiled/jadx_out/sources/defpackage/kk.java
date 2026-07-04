package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kk {
    public static final kk e;
    public static final kk f;
    public static final kk g;
    public static final kk h;
    public static final /* synthetic */ kk[] i;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [kk, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [kk, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r3v1, types: [kk, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r5v1, types: [kk, java.lang.Enum] */
    static {
        ?? r0 = new Enum("DEFAULT", 0);
        e = r0;
        ?? r1 = new Enum("LAZY", 1);
        f = r1;
        ?? r3 = new Enum("ATOMIC", 2);
        g = r3;
        ?? r5 = new Enum("UNDISPATCHED", 3);
        h = r5;
        i = new kk[]{r0, r1, r3, r5};
    }

    public static kk valueOf(String str) {
        return (kk) Enum.valueOf(kk.class, str);
    }

    public static kk[] values() {
        return (kk[]) i.clone();
    }
}
