package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v40 {
    public static final v40 e;
    public static final v40 f;
    public static final v40 g;
    public static final v40 h;
    public static final v40 i;
    public static final /* synthetic */ v40[] j;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [v40, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [v40, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r3v1, types: [v40, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r5v1, types: [v40, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r7v1, types: [v40, java.lang.Enum] */
    static {
        ?? r0 = new Enum("Measuring", 0);
        e = r0;
        ?? r1 = new Enum("LookaheadMeasuring", 1);
        f = r1;
        ?? r3 = new Enum("LayingOut", 2);
        g = r3;
        ?? r5 = new Enum("LookaheadLayingOut", 3);
        h = r5;
        ?? r7 = new Enum("Idle", 4);
        i = r7;
        j = new v40[]{r0, r1, r3, r5, r7};
    }

    public static v40 valueOf(String str) {
        return (v40) Enum.valueOf(v40.class, str);
    }

    public static v40[] values() {
        return (v40[]) j.clone();
    }
}
