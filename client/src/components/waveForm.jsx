import React, {
    useEffect,
    useState,
    useRef
} from "react";
import WaveSurfer from "wavesurfer.js";
import RegionsPlugin from "wavesurfer.js/dist/plugin/wavesurfer.regions.min";

export default function Waveform(props) {

    const [state, setState] = useState({
        wavesurfer: null,
        isPlaying: false,

    })
    const waveformRef = React.createRef();
    const {
        wavesurfer,
        isPlaying,
    } = state

    useEffect(() => {
        if (props.src) {
            draw();
        }
    }, [props.src])

    useEffect(() => {
        if (wavesurfer) {
            play();
        }
    }, [props.isPlaying])
    useEffect(() => {
        if (wavesurfer) {
            resetPlayhead()
        }

    }, [props.isAtBeginning])
    const removePrevious = () => {
        const chart = waveformRef.current;
        while (chart.hasChildNodes()) {
            chart.removeChild(chart.lastChild);
        }
    }
    const draw = () => {
        removePrevious();
        console.log("WaveSurfer", WaveSurfer.plugins)
        const wavesurfer = WaveSurfer.create({
            container: waveformRef.current,
            waveColor: "violet",
            progressColor: "purple",
            plugins: [
                RegionsPlugin.create({
                    regionsMinLength: 2,
                    regions: [{
                        id: "selected",
                        start: 0,
                        loop: false,
                        color: 'hsla(400, 100%, 30%, 0.5)'
                    }],
                    dragSelection: {
                        slop: 5
                    }
                })
            ]
        });
        console.log("xxx")
        setState({
            ...state,
            wavesurfer: wavesurfer
        });
        wavesurfer.load(props.src);

    }
    const play = () => {
        wavesurfer.playPause();
    }
    const resetPlayhead = () => {
        console.log("Xxx")
        wavesurfer.seekTo(0);
    }
    wavesurfer?.on('region-created', function (region) {
        console.log(region.start, region.end);
    });
    wavesurfer?.on('region-update-end', function (region) {
        props.onTrimming(region.start, region.end)
        var regions = region.wavesurfer.regions.list;
        var keys = Object.keys(regions);
        if (keys.length > 1) {
            regions[keys[0]].remove();
        }
    });
    console.log("state 2", state)
    return (
        <div ref={waveformRef} />
    );
}

Waveform.defaultProps = {
    src: ""
};